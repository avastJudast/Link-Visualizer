(async e => {
    let [activeTab] = await chrome.tabs.query({active: true, currentWindow: true})
    
    let scrapeBtn = document.querySelector('#scrape')
    console.log(scrapeBtn)

    scrapeBtn.addEventListener('click', () => {
        chrome.scripting.executeScript({
            target: {tabId: activeTab.id},
            function: getDomains,
        }, result => {
            let lastError = chrome.runtime.lastError
            if(lastError) console.log(lastError.message)
            
            let links = result?.[0].result || []
    
            chrome.storage.session.set({links})
            chrome.tabs.create({url: 'links.html'})
        })
    })
})()

function getDomains() {
	let urls = []

	for (const link of document.links) {
		try {
			let url = new URL(link.href)
			urls.push(url.hostname)
		} 
		catch (error) {
			console.log(error)
		}
	}

	return [...new Set(urls)]
}