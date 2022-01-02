const getImages = () => {
    return fetch('/api/images')
        .then(response => response.json())
        .catch(error => console.log(error));
}

(() => {
    console.log("It works :):)");

    getImages()
        //.then(images => //create html struct with products)
        .then(images => console.log(images))
})();