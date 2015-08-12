
// create walker to traverse through text node
var walker = document.createTreeWalker(
    document.body,
    NodeFilter.SHOW_TEXT,
    { acceptNode: function(node) {
        if ( ! /^\s*$/.test(node.data) ) {
            return NodeFilter.FILTER_ACCEPT;
        }
      }
    },
    false
);

// add position element around text nodes
walker.currentNode = document.body;
while(node = walker.nextNode()) {
    var position = document.createElement('scvnjr_position');
    // TODO unused var a
    var a = node.parentNode.replaceChild(position, node); // a unused
    position.appendChild(node);
}


// get original dimensions
var textNodes = [];
var textNodeCount = 0;
var textNode;
var node;
walker.currentNode = document.body;
while(node = walker.nextNode()) {
    var rect = node.parentNode.getBoundingClientRect();
    var styles = document.defaultView.getComputedStyle(node.parentNode);
    textNodes.push({
        //node: node.parentElement,
        text: node.nodeValue,
        left: Math.floor(rect.left),
        top: Math.floor(rect.top),
        right: Math.floor(rect.right),
        bottom: Math.floor(rect.bottom),
        width: Math.floor(rect.width),
        height: Math.floor(rect.height),
        color: styles.color,
        backgroundColor: styles.backgroundColor,
        fontFamily: styles.fontFamily,
        fontSize: styles.fontSize,
        fontStyle: styles.fontStyle,
        fontWeight: styles.fontWeight,
        textAlign: styles.textAlign,
    });
}

// change text to single character
walker.currentNode = document.body;
while(node = walker.nextNode()) {
    //node.originalValue = node.nodeValue;
    node.nodeValue = "A";
    node.parentNode.style.fontFamily = 'monospace';
    node.parentNode.style.fontSize = '10px'; //'0.7em';
    node.parentNode.style.fontVariant = 'normal';
    node.parentNode.style.verticalAlign = 'bottom';
}

// reset walker to ensure page has been rendered fully before
// accessing new dimensions
walker.currentNode = document.body;
textNodeCount = 0;
while(node = walker.nextNode()) {
    var rect = node.parentNode.getBoundingClientRect();
    textNode = textNodes[textNodeCount];

    textNode.cleft = Math.floor(rect.left);
    textNode.ctop = Math.floor(rect.top);
    textNode.cright = Math.floor(rect.right);
    textNode.cbottom = Math.floor(rect.bottom);
    textNode.cwidth = Math.floor(rect.width);
    textNode.cheight = Math.floor(rect.height);

    textNodeCount++;
}

// change text back to original value
// reset walker
walker.currentNode = document.body;
textNodeCount = 0;
while(node = walker.nextNode()) {
  textNode = textNodes[textNodeCount];
  node.nodeValue = textNode.text;
  textNodeCount++;
}

//// remove position element around text nodes
walker.currentNode = document.body;
while(node = walker.nextNode()) {
  node.parentNode.style.fontFamily = "";
  node.parentNode.style.fontSize = "";
  node.parentNode.style.fontVariant = "";
  node.parentNode.style.verticalAlign = "";
}

return textNodes;
