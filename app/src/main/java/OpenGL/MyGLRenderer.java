package OpenGL;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import Model.Foraminifera;
import Model.Shell;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "MainRenderer";

    private int mPerVertexProgramHandle;

    private int mMVPMatrixHandle;
    private int mMVMatrixHandle;
    private int mLightPosHandle;
    private int mPositionHandle;
    private int mColorHandle;

    private final int mPositionDataSize = 3;

    private float[] mModelMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    private float[] mAccumulatedRotation = new float[16];

    private final float[] mInnerSphereColor = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
    private final float[] mOuterSphereColor = new float[] {1.0f, 0.498038f, 0.0f, 1.0f};
    private final float[] mLightInitialPosition = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
    private final float[] mLightCalculatedPosition = new float[4];
    private final float[] mLightPosInEyeSpace = new float[4];

    public volatile float mDeltaRotationX;
    public volatile float mDeltaRotationY;
    public volatile float mDeltaTranslationX;
    public volatile float mDeltaTranslationY;
    public volatile float mScaleFactor = 1.0f;


    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        GLES20.glClearColor(0.3f, 0.6f, 0.3f, 1.0f);

        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        setProjectionMatrix();

        compileShaders();
    }

    private void compileShaders() {
        final String vertexShader = ShadersContainer.vertexShader;
        final String fragmentShader = ShadersContainer.fragmentShader;

        final int vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);

        mPerVertexProgramHandle = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
                new String[] {"a_Position"});
    }

    private void setProjectionMatrix() {
        // Position the eye in front of the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 7.0f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = 0.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to the same size as the surface.
        GLES20.glViewport(0, 0, width, height);

        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);

        Matrix.setIdentityM(mAccumulatedRotation, 0);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glUseProgram(mPerVertexProgramHandle);

        prepareShaderInputHandlers();

        iluminateScene();

        drawForaminifera();
    }

    private void prepareShaderInputHandlers() {
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mPerVertexProgramHandle, "u_MVPMatrix");
        mMVMatrixHandle = GLES20.glGetUniformLocation(mPerVertexProgramHandle, "u_MVMatrix");
        mLightPosHandle = GLES20.glGetUniformLocation(mPerVertexProgramHandle, "u_LightPos");
        mColorHandle = GLES20.glGetUniformLocation(mPerVertexProgramHandle, "u_Color");

        mPositionHandle = GLES20.glGetAttribLocation(mPerVertexProgramHandle, "a_Position");
    }

    private void iluminateScene() {
//        long time = SystemClock.uptimeMillis() % 10000L;
//        float lightRotationAngle = (360.0f / 1000.0f) * ((int) time);

        // Calculate position of the light. Rotate and then push into the distance.
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, 2.0f);
        //Matrix.rotateM(mModelMatrix, 0, -lightRotationAngle, 0.0f, 1.0f, 0.0f);
        Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, 2.0f);

        Matrix.multiplyMV(mLightCalculatedPosition, 0, mModelMatrix, 0, mLightInitialPosition, 0);
        Matrix.multiplyMV(mLightPosInEyeSpace, 0, mViewMatrix, 0, mLightCalculatedPosition, 0);
    }

    private void drawForaminifera() {
        Foraminifera foram = new Foraminifera();
        foram.addNextShell();
        foram.addNextShell();
        foram.addNextShell();
        foram.addNextShell();
        foram.addNextShell();

        for (Shell shell : foram.shells) {
            drawSphere(shell.outerSphere, mOuterSphereColor);
        }
    }

    private void drawSphere(Sphere sphere, float[] color) {
        translateModelToView();

        handleTouchScaling();
        handleTouchRotation();
        handleTouchTranslation();

        FloatBuffer spherePositions = sphere.sphereVerticesBuffer;
        spherePositions.position(0);

        GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false, 0, spherePositions);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glUniform4f(mColorHandle, color[0], color[1], color[2], color[3]);

        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        GLES20.glUniformMatrix4fv(mMVMatrixHandle, 1, false, mMVPMatrix, 0);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        GLES20.glUniform3f(mLightPosHandle, mLightPosInEyeSpace[0], mLightPosInEyeSpace[1], mLightPosInEyeSpace[2]);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, sphere.pointsCount);
    }

    private void translateModelToView() {
        Matrix.setIdentityM(mModelMatrix, 0);
        //Matrix.translateM(mModelMatrix, 0, 0.0f, 0.0f, -7.0f);
    }

    private void handleTouchScaling() {
        Matrix.scaleM(mModelMatrix, 0, mScaleFactor, mScaleFactor, mScaleFactor);
    }

    private void handleTouchRotation() {
        float[] mCurrentRotation = new float[16];

        Matrix.setIdentityM(mCurrentRotation, 0);
        Matrix.rotateM(mCurrentRotation, 0, mDeltaRotationX, 0.0f, 1.0f, 0.0f);
        Matrix.rotateM(mCurrentRotation, 0, mDeltaRotationY, 1.0f, 0.0f, 0.0f);
        mDeltaRotationX = 0.0f;
        mDeltaRotationY = 0.0f;

        Matrix.multiplyMM(mAccumulatedRotation, 0, mCurrentRotation, 0, mAccumulatedRotation, 0);
        Matrix.multiplyMM(mModelMatrix, 0, mModelMatrix, 0, mAccumulatedRotation, 0);
    }

    private void handleTouchTranslation() {
        float[] mCurrentTranslation = new float[16];

        Matrix.setIdentityM(mCurrentTranslation, 0);
        Matrix.translateM(mCurrentTranslation, 0, mDeltaTranslationX, -mDeltaTranslationY, 0.0f);
        mDeltaTranslationX = 0.0f;
        mDeltaTranslationY = 0.0f;

        Matrix.multiplyMM(mViewMatrix, 0, mViewMatrix, 0, mCurrentTranslation, 0);
    }


    private int compileShader(final int shaderType, final String shaderSource) {
        int shaderHandle = GLES20.glCreateShader(shaderType);

        if (shaderHandle != 0)
        {
            // Pass in the shader source.
            GLES20.glShaderSource(shaderHandle, shaderSource);

            // Compile the shader.
            GLES20.glCompileShader(shaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0)
            {
                Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
                GLES20.glDeleteShader(shaderHandle);
                shaderHandle = 0;
            }
        }

        if (shaderHandle == 0)
        {
            throw new RuntimeException("Error creating shader.");
        }

        return shaderHandle;
    }

    private int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle,
                                     final String[] attributes) {
        int programHandle = GLES20.glCreateProgram();

        if (programHandle != 0)
        {
            // Bind the vertex shader to the program.
            GLES20.glAttachShader(programHandle, vertexShaderHandle);

            // Bind the fragment shader to the program.
            GLES20.glAttachShader(programHandle, fragmentShaderHandle);

            // Bind attributes
            if (attributes != null)
            {
                final int size = attributes.length;
                for (int i = 0; i < size; i++)
                {
                    GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
                }
            }

            // Link the two shaders together into a program.
            GLES20.glLinkProgram(programHandle);

            // Get the link status.
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            // If the link failed, delete the program.
            if (linkStatus[0] == 0)
            {
                Log.e(TAG, "Error compiling program: " + GLES20.glGetProgramInfoLog(programHandle));
                GLES20.glDeleteProgram(programHandle);
                programHandle = 0;
            }
        }

        if (programHandle == 0)
        {
            throw new RuntimeException("Error creating program.");
        }

        return programHandle;
    }
}