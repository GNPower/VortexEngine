#version 430

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoord;
layout (location = 2) in vec3 normal;

out vec2 pass_textureCoord;
out vec3 pass_surfaceNormal;
out vec3 pass_worldPosition;
out vec3 pass_toCameraVector;

uniform mat4 m_MVP;
uniform mat4 m_Model;

uniform vec3 cameraPosition;

void main(void){

	pass_worldPosition = (m_Model * vec4(position, 1.0)).xyz;
	gl_Position = m_MVP * vec4(position, 1.0);
	
	pass_textureCoord = textureCoord;
	pass_surfaceNormal = (m_Model * vec4(normal, 0.0)).xyz;
	pass_toCameraVector = normalize(cameraPosition - pass_worldPosition);
}