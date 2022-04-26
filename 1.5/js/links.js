chrome.storage.session.get({links: []}, ({links}) => {
	links.sort()

	let alert = document.querySelector('.alert')
	if(!links.length) return alert.classList.remove('d-none')
		
	let listGroup = document.querySelector('.list-group')
	let markup = links.map(mapLink)
	listGroup.innerHTML = markup.join('\n')
	chrome.storage.session.set({links: []})
})

function mapLink(link, pos) {
	return `<a 
		href="http://${link}" 
		target="_blank" 
		class="list-group-item list-group-item-action">
		<strong>${pos + 1}:</strong> ${link}
	</a>`
}