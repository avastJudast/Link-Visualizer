chrome.runtime.sendMessage({popupOpen: true});
let scrape = document.getElementById("scrape");


scrape.addEventListener("click", () => {
  window.open("index.html", '_blank');
});
