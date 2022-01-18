const getImages = () => {
    return fetch('/api/images')
        .then(response => response.json())
        .catch(error => console.log(error));
}
const createHtmlElementFromString = (htmlAsString) => {


    return myElement.firstChild;
}

const createImageComponent = (image) => {
    const template = `
        <li class="product>
            <h3>${image.title}</h3>
            <div class="product_image">
                <img src="my_product_image"/>
            </div>
            <span class="product_price"></span>
            <button
                class="image_add_to_basket"
                data-image-id=${image.id}
                >Add to basket</button>
        </li>
    `;
    return createHtmlElementFromString(template);
}

(() => {
    console.log("It works :):)");

    getImages()
        //.then(images => //create html struct with products)
        .then(images => console.log(images))
})();