package OpenGL;

public class ShadersContainer {
    public static final String vertexShader =
            "uniform mat4 u_MVPMatrix;          \n"		// A constant representing the combined model/view/projection matrix.
            + "uniform mat4 u_MVMatrix;         \n"		// A constant representing the combined model/view matrix.
            + "uniform vec3 u_LightPos;         \n"	    // The position of the light in eye space.
            + "uniform vec4 u_Color;            \n"		// Passed in color
            + "uniform vec3 u_Clipping;         \n"		// Passed in clipping
            + "attribute vec4 a_Position;       \n"		// Per-vertex position information we will pass in.
            + "varying vec4 v_Color;            \n"		// This will be passed into the fragment shader.
            + "varying vec4 v_Position;         \n"		// This will be passed into the fragment shader.
            + "varying vec3 v_Clipping;         \n"		// This will be passed into the fragment shader.

            + "void main()                      \n" 	// The entry point for our vertex shader.
            + "{                                \n"
            + "   vec3 modelViewVertex = vec3(u_MVMatrix * a_Position);                 \n" // Transform the vertex into eye space.
            + "   vec3 modelViewNormal = vec3(u_MVMatrix);                              \n" // Transform the normal's orientation into eye space.
            + "   float distance = length(u_LightPos - modelViewVertex);                \n" // Will be used for attenuation.
            + "   vec3 lightVector = normalize(u_LightPos - modelViewVertex);           \n" // Get a lighting direction vector from the light to the vertex.
            + "   float diffuse = max(dot(modelViewNormal, lightVector), 2.5f);         \n" // Calculate the dot product of the light vector and vertex normal.
                                                                                            // If the normal and light vector are pointing in the same direction then it will get max illumination.
            + "   diffuse = diffuse * (1.0 / (1.0 + (0.2 * distance * distance)));      \n" // Attenuate the light based on distance.
            + "   v_Color = u_Color * diffuse;                                          \n" // Multiply the color by the illumination level. It will be interpolated across the triangle.
            + "   gl_Position = u_MVPMatrix * a_Position;                               \n" // gl_Position is a special variable used to store the final position.
                                                                                            // Multiply the vertex by the matrix to get the final point in normalized screen coordinates.
            + "   v_Position = a_Position;                                              \n" // pass position to fragment shader
            + "   v_Clipping = u_Clipping;                                              \n" // pass clipping to fragment shader
            + "}                                                                        \n";

    public static final String fragmentShader =
            "precision mediump float;           \n"		// Set the default precision to medium. We don't need as high of a precision in the fragment shader.
            + "varying vec4 v_Color;            \n"		// This is the color from the vertex shader interpolated across the triangle per fragment.
            + "varying vec4 v_Position;         \n"		// This is the position
            + "varying vec3 v_Clipping;         \n"		// This is the clipping

            + "void main()                      \n"		// The entry point for our fragment shader.
            + "{                                \n"
            + "   if (v_Position.z > v_Clipping.z){             \n"
            + "       discard;                                  \n"
            + "   } else if (v_Position.y > v_Clipping.y) {     \n"
            + "       discard;                                  \n"
            + "   } else if (v_Position.x > v_Clipping.x) {     \n"
            + "       discard;                                  \n"
            + "   } else {                                      \n"
            + "       gl_FragColor = v_Color;                   \n"
            + "   }                                             \n"
            + "}                                                \n";

    private ShadersContainer(){}
}
