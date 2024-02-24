#ifdef GL_ES
precision mediump float;
#endif

// glslsandbox uniforms
uniform float iTime;
uniform vec2 iResolution;

// shadertoy emulation
#define iiTime iTime
#define iiResolution vec3(iResolution,1.)

void mainImage(out vec4 O, vec2 I)
{
	vec3 p=iiResolution,d = -.5*vec3(I+I-p.xy,p)/p.x,c = d-d, i=c;
	for(;i.x<1.;c += length(sin(p.yx)+cos(p.xz+iTime))*d)
	p = c,
	p.z -= iTime+(i.x+=.01),
	p.xy *= mat2(sin((p.z*=.1)+vec4(0,11,33,0)));
	O = vec4(5,7,8,9)/length(c);
}

void main(void)
{
	mainImage(gl_FragColor, gl_FragCoord.xy);
	gl_FragColor.a = 2000.;
}
