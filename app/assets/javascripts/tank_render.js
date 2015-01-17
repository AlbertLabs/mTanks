var stage = new PIXI.Stage(0xBCBCBC);
var renderer = PIXI.autoDetectRenderer(700, 500);
document.getElementById("pixi-stage").appendChild(renderer.view);
var texture = PIXI.Texture.fromImage("../../assets/images/ship.png");
var tank = new PIXI.Sprite(texture);

initFirebase();

requestAnimFrame(animate);

function animate(){
    requestAnimFrame(animate);      
    renderer.render(stage);
}

function renderTank(obj){
    var _tank = new PIXI.Sprite(texture);
    _tank.anchor.x = 0.5;
    _tank.anchor.y = 0.5;
    _tank.position.x = obj.x;
    _tank.position.y = obj.y;
    setRotation(_tank, obj.heading);
    stage.addChild(_tank);
    
    //_tank.graphics = new PIXI.Graphics();
    //stage.addChild(_tank.graphics);
    
    //renderHealth(_tank, 100);
}

function initFirebase(){
    var fb = new Firebase("https://mtanks.firebaseio.com/");
    
    fb.on("value", function(snapshot){
        stage.removeChildren();
        snapshot.val().forEach(function(_tank) {
            renderTank(_tank);
        });
    });
}

function setRotation(_tank, degrees){
    _tank.rotation = ((90 + -degrees) * Math.PI / 180);
}

// function renderHealth(_tank, health){
//     _tank.graphics.clear();
//     _tank.graphics.beginFill(0xFF0000);
//     _tank.graphics.drawRect(_tank.position.x - 40, _tank.position.y - 70,80,10);
//     _tank.graphics.beginFill(0x00FF00);
//     _tank.graphics.drawRect(_tank.position.x - 40, _tank.position.y - 70, (health * 80 ) / 100,10);
//     _tank.graphics.endFill();
// }