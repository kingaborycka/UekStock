const getImages = () => {
    return fetch('/api/images')
        .then(response => response.json())
        .catch(error => console.log(error));
}

const createHtmlElementFromString = (htmlElementAsString) => {
    let myElement = document.createElement('div');
    myElement.innerHTML = htmlElementAsString.trim();
    return myElement.firstChild;
}

const createImageHtmlComponent = (image) => {
    const template = `
        <li class="image">
            <h3>${image.title}</h3>
            <div class="image_image-container">
                <img src="${image.mediaPath}"/>
            </div>
            <span class="image_price">${image.price}</span>
            <button
                class="image_add-to-basket"
                data-image-id="${image.id}"
            >
                Add to basket
            </button>
        </li>
    `;

    return createHtmlElementFromString(template);
}

const appendImagesToList = (listEl, imagesAsHtml) => {
    imagesAsHtml.forEach(el => listEl.appendChild(el));

    return imagesAsHtml;
}

const refreshCurrentOffer = () => {
    const basketEl = document.querySelector('.basket');
    fetch('/api/current-offer')
        .then(r => r.json())
        .then(offer => {
            basketEl.querySelector('.basket__total').innerText = `${offer.total} PLN`;
            basketEl.querySelector('.basket__items-count').innerText = `${offer.linesCount} Items`;
        })
}

const initializeAddToBasketHandler = (imageHtmlEl) => {
    const addToBasketBtn = imageHtmlEl.querySelector('.image__add-to-basket');

    addToBasketBtn.addEventListener('click', (event) => {
        const imageId = event.target.getAttribute('data-image-id');
        const url = `/api/add-image/${imageId}`;

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
        })
            .then(() => refreshCurrentOffer())
            .catch(err => console.log(err));
    })
}

const initializeAddToBasketHandling = (imagesAsHtmlEl) => {
    imagesAsHtmlEl.forEach(el => initializeAddToBasketHandler(el))
    return imagesAsHtmlEl;
}

const handleCheckout = () => {
    console.log('lets do some checkout steps');
}

(() => {
    console.log("It works :)");
    const imagesList = document.getElementById('images');
    getImages()
        .then(imagesAsObjects => imagesAsObjects.map(createImageHtmlComponent))
        .then(imagesAsHtmlEl => initializeAddToBasketHandling(imagesAsHtmlEl))
        .then(imagesAsHtmlEl => appendImagesToList(imagesList, imagesAsHtmlEl))
        .then(images => console.log(images))
    ;

    refreshCurrentOffer();

    const checkoutBtn = document.querySelector('.basket__checkout');
    checkoutBtn.addEventListener('click', handleCheckout);
})();