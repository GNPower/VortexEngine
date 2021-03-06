#version 430

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoord;

out vec2 pass_textureCoord;

uniform mat4 m_MVP;

void main(void){
	
	gl_Position = m_MVP * vec4(position, 1.0);
	
	pass_textureCoord = textureCoord;
}