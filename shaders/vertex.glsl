#version 120

attribute vec3 vertices;
attribute vec2 textures;

void main() {
    gl_Position = vec4(vertices, 1);
}