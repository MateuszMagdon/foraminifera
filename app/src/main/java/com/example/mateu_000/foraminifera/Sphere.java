package com.example.mateu_000.foraminifera;

import android.opengl.GLES20;
import android.util.FloatMath;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by mateu_000 on 2015-05-04.
 */
public class Sphere {
    private int stacks;
    private int slices;
    private float radius;

    //Buffers
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private ShortBuffer indexBuffer;

    //Buffer sizes in aantal bytes
    private int vertexBufferSize;
    private int colorBufferSize;
    private int indexBufferSize;

    private int vertexCount;

    private int program;

    static final int FLOATS_PER_VERTEX = 3; // Het aantal floats in een vertex (x, y, z)
    static final int FLOATS_PER_COLOR = 4;  // Het aantal floats in een kleur (r, g, b, a)
    static final int SHORTS_PER_INDEX = 2;
    static final int BYTES_PER_FLOAT = 4;
    static final int BYTES_PER_SHORT = 2;

    static final int BYTES_PER_VERTEX = FLOATS_PER_VERTEX * BYTES_PER_FLOAT;
    static final int BYTES_PER_COLOR = FLOATS_PER_COLOR * BYTES_PER_FLOAT;
    static final int BYTES_PER_INDEX_ENTRY = SHORTS_PER_INDEX * BYTES_PER_SHORT;

    // Set color with red, green, blue and alpha (opacity) values
    private float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    public Sphere(float radius, int stacks, int slices)
    {
        this.stacks = stacks;
        this.slices = slices;
        this.radius = radius;

        vertexCount         = (stacks+1) * (slices+1);
        vertexBufferSize    = vertexCount * BYTES_PER_VERTEX;
        colorBufferSize     = vertexCount * BYTES_PER_COLOR;
        indexBufferSize     = vertexCount * BYTES_PER_INDEX_ENTRY;

        program = GLHelpers.createProgram();
        if (program == 0) {
            return;
        }
        GLHelpers.checkGlError("program");

        // Setup vertex-array buffer. Vertices in float. A float has 4 bytes.
        vertexBuffer = ByteBuffer.allocateDirect(vertexBufferSize).order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorBuffer = ByteBuffer.allocateDirect(colorBufferSize).order(ByteOrder.nativeOrder()).asFloatBuffer();
        indexBuffer = ByteBuffer.allocateDirect(indexBufferSize).order(ByteOrder.nativeOrder()).asShortBuffer();

        generateSphereCoords(radius, stacks, slices);

        vertexBuffer.position(0);
        colorBuffer.position(0);
        indexBuffer.position(0);
    }


    public void draw(float[] modelViewProjectionMatrix)
    {
        GLES20.glUseProgram(program);

        GLHelpers.checkGlError("useprogram");

        int positionHandle = GLES20.glGetAttribLocation(program, "a_Position");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, BYTES_PER_VERTEX, vertexBuffer);
        GLHelpers.checkGlError("pos");

        //int colorHandle = GLES20.glGetAttribLocation(program, "a_Color");
        //GLES20.glEnableVertexAttribArray(colorHandle);
        //GLES20.glVertexAttribPointer(colorHandle, 4, GLES20.GL_FLOAT, false, BYTES_PER_COLOR, colorBuffer);
        //GLHelpers.checkGlError("color");

        int matrixHandle = GLES20.glGetUniformLocation(program, "u_Matrix");
        GLES20.glUniformMatrix4fv(matrixHandle, 1, false, modelViewProjectionMatrix, 0);

        /*
         * When using glDrawArrays rendering works but the results are not correct, when using glDrawElements I get an GL_INVALID_OPERATION error.
         */
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, indexBuffer.capacity(), GLES20.GL_SHORT, indexBuffer);
        //GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount);

        GLHelpers.checkGlError("draw");

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
        //GLES20.glDisableVertexAttribArray(colorHandle);
    }

    private void generateSphereCoords(float radius, int stacks, int slices)
    {
        for (int stackNumber = 0; stackNumber <= stacks; ++stackNumber)
        {
            for (int sliceNumber = 0; sliceNumber < slices; ++sliceNumber)
            {
                float theta = (float) (stackNumber * Math.PI / stacks);
                float phi = (float) (sliceNumber * 2 * Math.PI / slices);
                float sinTheta = FloatMath.sin(theta);
                float sinPhi = FloatMath.sin(phi);
                float cosTheta = FloatMath.cos(theta);
                float cosPhi = FloatMath.cos(phi);
                vertexBuffer.put(new float[]{radius * cosPhi * sinTheta, radius * sinPhi * sinTheta, radius * cosTheta});
            }
        }

        for (int stackNumber = 0; stackNumber < stacks; ++stackNumber)
        {
            for (int sliceNumber = 0; sliceNumber <= slices; ++sliceNumber)
            {
                indexBuffer.put((short) ((stackNumber * slices) + (sliceNumber % slices)));
                indexBuffer.put((short) (((stackNumber + 1) * slices) + (sliceNumber % slices)));
            }
        }
    }

//    public static Model3D createSphere(float radius, int stacks, int slices)
//    {
//        int vertexCount = (stacks + 1) * (slices + 1);
//        FloatBuffer vertexBuffer        = ByteBuffer.allocateDirect(vertexCount * GLHelpers.BYTES_PER_VERTEX).order(ByteOrder.nativeOrder()).asFloatBuffer();
//        FloatBuffer normalBuffer        = ByteBuffer.allocateDirect(vertexCount * GLHelpers.BYTES_PER_NORMAL).order(ByteOrder.nativeOrder()).asFloatBuffer();
//        FloatBuffer textureCoordBuffer  = ByteBuffer.allocateDirect(vertexCount * GLHelpers.BYTES_PER_TEXTURE_COORD).order(ByteOrder.nativeOrder()).asFloatBuffer();
//        ShortBuffer indexBuffer         = ByteBuffer.allocateDirect(vertexCount * GLHelpers.BYTES_PER_TRIANGLE_INDEX).order(ByteOrder.nativeOrder()).asShortBuffer();
//
//        for (int stackNumber = 0; stackNumber <= stacks; ++stackNumber)
//        {
//            for (int sliceNumber = 0; sliceNumber <= slices; ++sliceNumber)
//            {
//                float theta = (float) (stackNumber * Math.PI / stacks);
//                float phi = (float) (sliceNumber * 2 * Math.PI / slices);
//                float sinTheta = FloatMath.sin(theta);
//                float sinPhi = FloatMath.sin(phi);
//                float cosTheta = FloatMath.cos(theta);
//                float cosPhi = FloatMath.cos(phi);
//
//                float nx = cosPhi * sinTheta;
//                float ny = cosTheta;
//                float nz = sinPhi * sinTheta;
//
//
//                float x = radius * nx;
//                float y = radius * ny;
//                float z = radius * nz;
//
//                float u = 1.f - ((float)sliceNumber / (float)slices);
//                float v = (float)stackNumber / (float)stacks;
//
//
//
//                normalBuffer.put(nx);
//                normalBuffer.put(ny);
//                normalBuffer.put(nz);
//
//                vertexBuffer.put(x);
//                vertexBuffer.put(y);
//                vertexBuffer.put(z);
//
//                textureCoordBuffer.put(u);
//                textureCoordBuffer.put(v);
//            }
//        }
//
//        for (int stackNumber = 0; stackNumber < stacks; ++stackNumber)
//        {
//            for (int sliceNumber = 0; sliceNumber < slices; ++sliceNumber)
//            {
//                int second = (sliceNumber * (stacks + 1)) + stackNumber;
//                int first = second + stacks + 1;
//
//                //int first = (stackNumber * slices) + (sliceNumber % slices);
//                //int second = ((stackNumber + 1) * slices) + (sliceNumber % slices);
//
//                indexBuffer.put((short) first);
//                indexBuffer.put((short) second);
//                indexBuffer.put((short) (first + 1));
//
//                indexBuffer.put((short) second);
//                indexBuffer.put((short) (second + 1));
//                indexBuffer.put((short) (first + 1));
//            }
//        }
//
//        vertexBuffer.rewind();
//        normalBuffer.rewind();
//        indexBuffer.rewind();
//        textureCoordBuffer.rewind();
//
//        Model3D sphere = new Model3D().setVertexBuffer(vertexBuffer)
//                .setNormalBuffer(normalBuffer)
//                .setIndexBuffer(indexBuffer)
//                .setTexture(R.drawable.earth)
//                .setTextureCoordBuffer(textureCoordBuffer)
//                .setDiffuseLighting(-3f, 2.3f, 2f);
//        return sphere;
//
//    }
}
