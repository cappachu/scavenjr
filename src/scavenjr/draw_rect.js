
//Position parameters used for drawing the rectangle
var left = arguments[0];
var top = arguments[1];
var right = arguments[2];
var bottom = arguments[3];
var color = arguments[4];

var width = right - left + 1;
var height = bottom - top + 1;


var canvas = document.getElementById("scvnjr_canvas");
var context = canvas.getContext('2d');
//Draw rectangle
// context retains previous state of path, so we must reset path
context.beginPath();
context.rect(left, top, width, height);
context.strokeStyle = color;//"red";
context.lineWidth = 2;
context.stroke();
