#version 430

in vec2 pass_textureCoord;

layout (location = 0) out vec4 colour;

uniform sampler2D diffuse_map;

void main(void){
	
	colour = texture(diffuse_map, pass_textureCoord);
}