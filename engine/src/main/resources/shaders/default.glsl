#type vertex
#version 460 core

layout(location = 0) in vec2 aPos;
layout(location = 1) in vec4 aColor;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;

void main(){
    fColor = aColor;

    gl_Position = uProjection*uView*vec4(aPos, 0, 1);
}

// -----------------------------------------------------------------------------
#type fragment
#version 460 core

in vec4 fColor;

out vec4 color;

void main(){
    color = fColor;
}