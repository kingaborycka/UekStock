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
                <img src="${image.filePath}"/>
            </div>
            <span class="image_price">${image.price}</span>
            <button
                class="image_add-to-basket"
                data-image-id="${image.imageId}"
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
            basketEl.querySelector('.basket_total').innerText = `${offer.total} PLN`;
            basketEl.querySelector('.basket_items-count').innerText = `${offer.linesCount} Items`;
        })
}

const initializeAddToBasketHandler = (imageHtmlEl) => {
    const addToBasketBtn = imageHtmlEl.querySelector('.image_add-to-basket');

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
    refreshCurrentOffer();
}

const showCheckout = () => {
    const checkoutLayer = document.querySelector('.checkout_layer');
    checkoutLayer.style.display = 'block';
}

const closeCheckout = () => {
    console.log('close');
    const checkoutLayer = document.querySelector('.checkout_layer');
    checkoutLayer.style.display = 'none';
}

(() => {
    const imagesList = document.getElementById('images');
    getImages()
        .then(imagesAsObjects => imagesAsObjects.map(createImageHtmlComponent))
        .then(imagesAsHtmlEl => initializeAddToBasketHandling(imagesAsHtmlEl))
        .then(imagesAsHtmlEl => appendImagesToList(imagesList, imagesAsHtmlEl))
        .then(images => console.log(images))
    ;

    refreshCurrentOffer();

    const checkoutBtn = document.querySelector('.basket_checkout');
    checkoutBtn.addEventListener('click', showCheckout);

    const closeCheckoutBtn = document.querySelector('.checkout_close');
    closeCheckoutBtn.addEventListener('click', closeCheckout);

    const checkoutForm = document.querySelector('.checkout_form');
    checkoutForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(checkoutForm);
        const data = {};

        formData.forEach((value, key) => (data[key] = value));

        fetch(checkoutForm.getAttribute('action'), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(res => res.json())
            .then(reservationDetails => {
                checkoutForm.reset();
                window.location.href = reservationDetails.paymentUrl;
            });

    });

    console.log("It works :)");
})();