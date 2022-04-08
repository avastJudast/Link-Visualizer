let scrape = document.getElementById("scrape");

chrome.storage.sync.get("color", ({ color }) => {
  scrape.style.backgroundColor = 'green';
});
// When the button is clicked, inject setPageBackgroundColor into current page
scrape.addEventListener("click", async () => {
  let [tab] = await chrome.tabs.query({ active: true, currentWindow: true });
  console.log("THis is url", tab.url);
  chrome.scripting.executeScript({
    target: { tabId: tab.id },
    function: getURLs,
  }, function(result) {
    console.log(result[0]);
  });
  scrape.style.backgroundColor = 'salmon';
  document.getElementById("scrape").innerHTML = "clicked";
});
// The body of this function will be executed as a content script inside the
// current page
function getURLs() {
  var urls = [];
  for(var i = 0; i < document.links.length; i ++) {
      if(document.links[i].hostname === location.hostname)
          urls.push(document.links[i].href);
  }
  return urls;
}