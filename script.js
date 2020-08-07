/*
@author Bassel Rezkalla
@date 2020/04/09
*/

// https://jsfiddle.net/u5ogmh9a/
// ^ A resource for moving things as we drag around

//console.log("heLlO wOrLD");

const buttonOffset = 39;
const parentSubsPerPage = 10;

var myCanvas = document.querySelector('canvas');
var c = myCanvas.getContext('2d');

//Will store a list of nodes
// var nodeArray; 

let m = new Map();

var hoverAlpha = 0.3;


var totalNodeArray = [];
var currentPointer = 0;
var currNodeArray;

myCanvas.width = window.innerWidth - 100;
myCanvas.height = window.innerHeight - 100;

var nodeRadius = 30;

// Minimum distance between any 2 nodes drawn
var minDist = 3 * nodeRadius;

var mouse = {
    x: null,
    y: null
}

function refresh(){
    if(currentPointer > 9){
        currentPointer = 0;
    }
    currNodeArray = totalNodeArray[currentPointer++];
}

// Mouse listener
window.addEventListener('mousemove', 
    function(event){
        //var rect = event.getBoundingClientRect();
        mouse.x = event.clientX;
        mouse.y = event.clientY - 48;
        //console.log(mouse);
    }
)

//onUpload event is fired from <input> once user uploads a file
function onUpload(e){
    // Makes the msg & input button invisible and the canvas visible
    document.getElementById("jsonFile").style.display = "none";
    document.getElementById("msg").style.display = "none";
    document.getElementById("myCanvas").style.display = "block";
    document.getElementById("refreshDiv").style.display = "block";

    //console.log("Event occured: onUpload");
    let jsonFile = document.getElementById('jsonFile').files[0];    //Stores uploaded file in midiFile
    var fr = new FileReader();

    fr.readAsText(jsonFile);    //Reads file as text, stores in fr.result
    //console.log("bar");


    //fr.onload event is fired once fr.readAsText finishes loading file
    // *** fr.onload is ASYNCHRONOUS ***
    fr.onload = function(){

        //console.log("foo");
        //console.log("Event occured: FileReader onLoad");
        masterList = JSON.parse(fr.result);    //JSON parses the JSON text stored in uploaded file (midiFile)

        // Main code goes here (since main code should happen AFTER onload() is fired (fr.readAsText finishes))
        
        for(var i = 0; i < masterList.length / parentSubsPerPage; i++){
            let nodeArray = [];
            //Populating nodeArray
            for(var k = parentSubsPerPage * i; k < parentSubsPerPage * (i+1); k++){
                //console.log("i: " + i + " - " + masterList[i]);
                var name = masterList[k].name;
                var pos = uniquePos(nodeArray);
                var x = pos[0];
                var y = pos[1];
                var adj = [];
                var adjCir = [];

                //Adding adjacent nodes to adjList and nodeArray
                for(var j = 0; j < masterList[k].adj.length; j++){
                    adj.push(masterList[k].adj[j]);
                    var adjName = masterList[k].adj[j].name;

                    var adjPos = uniquePos(nodeArray);

                    // Ensures that this position is unique to parent subreddit position
                    while(pointDist(adjPos[0], adjPos[1], x, y,) < minDist){
                        adjPos = uniquePos(nodeArray);
                    }

                    var adjX = adjPos[0];
                    var adjY = adjPos[1];
                    var ch = new Circle(adjName, adjX, adjY, nodeRadius, null, null, name);
                    adjCir.push(ch);
                    nodeArray.push(ch);
                    m.set(adjName, ch);
                }
                var chp = new Circle(name, x, y, nodeRadius, adj, adjCir, null);
                nodeArray.push(chp);
                m.set(name, chp);
            }
            totalNodeArray.push(nodeArray);
        }

        currNodeArray = totalNodeArray[currentPointer];
        currentPointer++;

        console.log("Current node array [0] type: " + typeof(currNodeArray[0]));

        animate();
    }
}

// Calculates the distance between 2 points
function pointDist(fromX, fromY, toX, toY){
    var a = fromX - toX;
    var b = fromY - toY;
    return Math.sqrt(a*a + b*b);
}

// Generates a unique position (one such that it is not closer than minDist for any circle in nodeArray)
function uniquePos(nodeArray){
    result = [];

    var unique = false;

    while(!unique){

        unique = true;


        // x can be between 2 radii from the left to 3 radii from the right
        var x = 2 * nodeRadius + (Math.random() * (myCanvas.width - (6 * nodeRadius)));
        // y can be between 2 radii from the top to 3 radius from the bottom
        var y = 2 * nodeRadius + (Math.random() * (myCanvas.height - (3 * nodeRadius)));

        for(i in nodeArray){
            if(pointDist(x, y, nodeArray[i].x, nodeArray[i].y) < minDist){
                unique = false;
                break;
            }
        }

        if(unique){
            result.push(x);
            result.push(y);
            return result;
        }
    }
    
}

class Circle{

    // Circle constructor (includes adjacency list)
    constructor(name, x, y, radius, adj, adjCir, parent){
        this.name = name;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.adj = adj;
        this.adjCir = adjCir;
        this.parent = parent;
    }

    dist(c){
        var a = this.x - c.x;
        var b = this.y - c.y;
        return Math.sqrt(a*a + b*b);
    }

    // Display the circle
    draw(alpha){
        c.beginPath();
        c.arc(this.x, this.y, this.radius, 0, 2 * Math.PI, false);
        c.strokeStyle = 'rgba(255, 0, 0, 1)';
        c.stroke();
        //c.fillStyle = 'rgba(100, 0, 0, 0.1)';
        c.fillStyle = "rgba(100, 0, 0, " + alpha.toString() + ")";
        c.fill();
    }

    arrow(toCircle, a){
        var headlen = 10;
        var dx = this.x - toCircle.x;
        var dy = this.y - toCircle.y;
        var ang = Math.atan2(dy, dx);
        var xoff = this.radius * Math.cos(ang);
        var yoff = this.radius * Math.sin(ang);
        
        c.beginPath();
        
        c.moveTo(this.x - xoff, this.y - yoff);

        c.lineTo(toCircle.x + xoff, toCircle.y + yoff);
        c.lineTo(toCircle.x + xoff + headlen * Math.cos(ang - Math.PI / 6), toCircle.y + yoff + headlen * Math.sin(ang - Math.PI / 6));
        
        c.lineTo(toCircle.x + xoff, toCircle.y + yoff);
        c.lineTo(toCircle.x + xoff + headlen * Math.cos(ang + Math.PI / 6), toCircle.y + yoff + headlen * Math.sin(ang + Math.PI / 6));

        c.strokeStyle = "rgba(150, 0, 0, " + a.toString() + ")";
        c.stroke();
        //c.closePath();
    }

    update(){
        this.draw(0.1);
        //this.displayName();
        this.drawArrows(0.3);
        this.hover(2);
    }

    // *Incomplete* Draw arrow from a node to its adjacent nodes
    drawArrows(a){
        if(this.adjCir != null){
            for(var p = 0; p < this.adjCir.length; p++){
                this.arrow(this.adjCir[p], a);
                
            }
        }
    }
    parentChild(b){
        this.displayName();
        this.drawArrows(b);
        this.draw(hoverAlpha)
        for(var u = 0; u < this.adj.length; u++){
            this.adjCir[u].displayName();
            this.adjCir[u].drawArrows(b);
            this.adjCir[u].draw(hoverAlpha);
        }
    }
    // *Incomplete* Display information about a node when the node is hovered over
    hover(b){
        if(Math.abs(mouse.x - this.x) < this.radius && Math.abs((mouse.y - this.y) - buttonOffset) < this.radius){
            if(this.adj != null){
                this.parentChild(b);
            }
            
            if(this.parent != null){
                m.get(this.parent).parentChild(b);
            }
        }
    }

    displayName(){
        c.font = "30px Arial";
        c.fillStyle = 'rgba(255, 60, 0, 0.9)';
        c.textAlign = 'center';
        c.fillText(this.name, this.x, this.y - (1.4 * nodeRadius));
    }
}

function animate(){
    requestAnimationFrame(animate);
    c.clearRect(0, 0, window.innerWidth, window.innerHeight);
    for(var i = 0; i < currNodeArray.length; i++){
        currNodeArray[i].update();
    }
    
}
