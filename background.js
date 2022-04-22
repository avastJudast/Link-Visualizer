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
  return urls;
}

