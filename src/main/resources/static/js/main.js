const getImages = () => {
    return fetch('/api/images')
        .then(response => response.json())
        .catch(error => console.log(error));
}
const handleAddToBasket = (imageId) => {
    const url = `/api/add-image/${imageId}`;
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    });
}

const refreshCurrentOffer = () => {
    return fetch('/api/current-offer')
        .then(r => r.json())
        .then(offer => updateBasketComponent(offer))
}

const createHtmlElementFromString = (htmlAsString) => {
    let myElement = document.createElement('div');
    myElement.innerHTML = htmlAsString.trim();
    return myElement.firstChild;
}

const createImageComponent = (image) => {
    const template = `
        <li class="image">
            <h3>${image.title}</h3>
            <div class="image_image">
                <img src="${image.mediaPath}" />
            </div>
            <span class="image_price">${image.price}</span>
            <button
                class="image_add-to-basket"
                data-image-id="${image.id}"
            >Add to basket</button>
        </li>
    `;

    return createHtmlElementFromString(template);
}

const appendToImageList = (htmlList, htmlElements) => {
    htmlElements.forEach(element => htmlList.appendChild(element));

    return htmlElements;
}

const updateBasketComponent = (offer) => {
    const basketEl = document.querySelector('.basket');
    basketEl.querySelector('.basket_items-count').innerText = `${offer.linesCount} Items`;
    basketEl.querySelector('.basket_total').innerText = `${offer.total} PLN`;
}

const initializeAddToBasketHandler = (htmlElements) => {
    htmlElements.forEach(element => {
        const addToBasketBtn = element.querySelector('.image_add-to-basket');

        addToBasketBtn.addEventListener('click', (event) => {
            const imageId = event.target.getAttribute('data-image-id');
            handleAddToBasket(imageId)
                .then(() => refreshCurrentOffer())
                .catch((err) => console.log('something went wrong :C'))
        });
    })
    return htmlElements;
}

(() => {
    console.log("It works :):)");

    const imagesList = document.querySelector('.images_list');
    const buyBtn = document.getElementById('buy');

    buyBtn.addEventListener('click', () => {
        console.log('accept offer needs to be implemented');
    })

    refreshCurrentOffer()
        .then(() => console.log('offer should be refreshed'));

    getImages()
        .then(imagesAsJsonObject => imagesAsJsonObject.map(createImageComponent))
        .then(imagesAsHtmlEl => initializeAddToBasketHandler(imagesAsHtmlEl))
        .then(imagesAsHtmlEl => appendToImageList(imagesList, imagesAsHtmlEl))
})();