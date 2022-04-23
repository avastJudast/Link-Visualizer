chrome.runtime.onMessage.addListener(function (message, sender, sendResponse) {
  //console.log(message);
  if (message.popupOpen) {
    //console.log("got milk?");
    const promise = new Promise(async (resolve) => {
      let [tab] = await chrome.tabs.query({ active: true, currentWindow: true });
      resolve(tab);
      //console.log("THis is url", tab.url);
      chrome.scripting.executeScript({
        target: { tabId: tab.id },
        function: getURLs,
      }, function(result) {
        console.log(result[0]);
      });
    });

    promise.then((tab) => {
      sendResponse(tab);
    });
  }
  return true;
});
function getURLs() {
  var urls = [];
  for(var i = 0; i < document.links.length; i ++) {
    urls.push(document.links[i].href);
  }
  var input = [];
  urls.sort()
  urls.forEach((url)=>{
    if(!input.includes(url)){
      input.push(url)
    }
  });
  var output = []
  //now we have individual links with no repeats in sorted order
  for(i = 0; i < input.length; i++){
    output.push(input[i].substring(0, input[i].indexOf("/", 9)));
  }
  output.forEach((url)=>{
    if(!output.includes(url)){
      output.push(recursiveFunc(output,input))
    }
  });
  return output;
}
function recursiveFunc(url,all){
  var output = [];
  all.forEach((input)=>{
    if(input.inlcudes(url,0) && input.indexOf("/",url.length + 1)!=-1){
      output.push(input.subString(0,input.indexOf("/",url.length)+1));
    }else if(input.includes(url,0) && !(input === url)){
      output.push(input)
    }
  });
  for(var i = 0; i < output.length;i++){
    output.splice(i,0,recursiveFunc(output[i],all))
  }
  return output;
}