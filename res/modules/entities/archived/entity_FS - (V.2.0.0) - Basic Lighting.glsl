#version 430

in vec2 pass_textureCoord;
in vec3 pass_surfaceNormal;
in vec3 pass_worldPosition;
in vec3 pass_toCameraVector;

layout (location = 0) out vec4 colour;

struct Light {
	vec3 position;
	vec3 colour;
	vec3 diffuseIntensity;
	vec3 specularIntensity;
};

uniform sampler2D diffuse_map;
uniform sampler2D specular_map;
uniform Light light;

const vec4 tex = texture(diffuse_map, pass_textureCoord);
const vec3 tex3 = vec3(tex);

const vec3 spec3 = vec3(texture(specular_map, pass_textureCoord));

const vec3 ambientStrength = vec3(0.05, 0.05, 0.05);

//calculates the ambient light additon to the total fragment light
//ambient light is calculated once per scene and serves to ensure all objects are somewhat lit up
vec3 ambient(){
	return ambientStrength * tex3;
}

//calculates the diffuse light additon to the total fragment light
vec3 diffuse(vec3 normal, vec3 lightDir, vec3 lightColour, vec3 diffuseIntensity){
	float diff = max(dot(normal, lightDir), 0.0);
	return diffuseIntensity * diff * tex3 * lightColour;
}

//calculates the specular light additon to the total fragment light
vec3 specular(vec3 normal, vec3 light, vec3 lightColour, vec3 cameraDir, vec3 specularIntensity){
	
	vec3 reflected = reflect(-light, normal);
	float spec = pow(max(dot(cameraDir, reflected), 0.0), 32.0);
	return specularIntensity * spec * spec3 * lightColour;
}

void main(void){
	
	//calculates vectors necessary for lighting calculations
	vec3 unitNormal = normalize(pass_surfaceNormal);
	vec3 toLightVector = normalize(light.position - pass_worldPosition);
	vec3 result = vec3(0.0);
	
	result += diffuse(unitNormal, toLightVector, light.colour, light.diffuseIntensity) 
	+ specular(unitNormal, toLightVector, light.colour, pass_toCameraVector, light.specularIntensity);
	
	colour = vec4(result + ambient(), 1.0);
}