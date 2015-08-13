
var canvas = document.createElement('canvas'); 

//Set canvas drawing area width/height
var body = document.body,
    html = document.documentElement;
var height = Math.max( body.scrollHeight, body.offsetHeight,
                       html.clientHeight, html.scrollHeight, html.offsetHeight );
var width = Math.max(  body.scrollHeight, body.offsetHeight,
                       html.clientHeight, html.scrollHeight, html.offsetHeight );

canvas.width = width;
canvas.height = height;

//Position canvas
canvas.style.position='absolute';
canvas.style.left=0;
canvas.style.top=0;
canvas.style.zIndex=100000;
canvas.style.pointerEvents='none'; //Make sure you can click 'through' the canvas
canvas.id = "scavenjr_canvas";
document.body.appendChild(canvas); //Append canvas to body element

