//Products Table available
document.addEventListener("DOMContentLoaded", function () {
    const productList = document.getElementById('productList');
    const searchForm = document.querySelector('form');

    // Fetch all products initially
    fetchProducts();

    searchForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const searchInput = document.getElementById('searchProduct');
        const query = searchInput.value;

        // Fetch products based on the search query
        fetchProducts(query);
    });

    function fetchProducts(query = '') {
        // Clear the existing product list
        productList.innerHTML = '';

        // Fetch products from the backend
        const apiUrl = query ? `http://localhost:8080/products/search?query=${query}` : 'http://localhost:8080/products';

        fetch(apiUrl)
            .then(response => response.json())
            .then(products => {
                // Update the UI with the product list
                products.forEach(product => {
                    const productCard = document.createElement('div');
                    const productCardImg = document.createElement('div');
                    const img = document.createElement('img');
                    const h2 = document.createElement('h2');
                    const addButton = document.createElement('button');
                    const orderButton = document.createElement('button');

                    img.src = product.productImage;
                    img.width = '140';
                    img.height = '60';
                    img.align = 'center';

                    productCard.className = 'product-card';
                    productCard.appendChild(img);

                    productCard.innerHTML += `<p>${product.productID}</p><p>  ${product.productName}</p><p> Cost: ${product.productPrice}</p><p> Quantity: ${product.noOfProductsAvailable}</p>`;

                    addButton.innerText = 'Add To Cart';
                    orderButton.innerText = 'Buy';
                    addButton.addEventListener('click', () => {
                        addToCartItem(product.productID);
                    });

                    orderButton.addEventListener('click', function(){
                        window.location.href = "http://localhost:63342/EcommerceApp/static/orderItem.html";
                    });

                    productCard.appendChild(addButton);
                    productCard.appendChild(orderButton);
                    productList.appendChild(productCard);
                });
            })
            .catch(error => console.error('Error:', error));
    }

    function addToCartItem(productID) {
        // Make a POST request to add the item to the cart
        fetch('http://localhost:8080/cart/addToCart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                product: {
                    productID: productID,
                },
                quantity: 1, // You can set the quantity as needed
            }),
        })
            .then(response => response.json())
            .then(data => console.log('Item added to cart:', data))
            .catch(error => console.error('Error adding item to cart:', error));
    }
});



//Cart Products Table will display
document.addEventListener("DOMContentLoaded", function () {
    const productList = document.getElementById('cartList');

    // Fetch products from the backend
    fetch('http://localhost:8080/cart/getAllCartItems')
        .then(response => response.json())
        .then(products => {
            // Update the UI with the product list
            products.forEach(product => {
                const productCard = document.createElement('div');
                const productCardImg = document.createElement('div')
                const img = document.createElement('img');
                const h2 = document.createElement('h2');
                const removeButton = document.createElement('button');

                img.src = product.product.productImage;
                img.width = '140';
                img.height = '60';
                img.align = 'center';

                productCard.className = 'product-card';
                productCard.appendChild(img);

                productCard.innerHTML += `<p>${product.cartID}</p><p>${product.product.productID}</p><p>  ${product.product.productName}</p><p> Cost: ${product.product.productPrice}</p><p> Quantity: ${product.quantity}</p>`;

                removeButton.innerText = 'Remove From Cart';
                removeButton.addEventListener('click', () => {
                    removeCartItem(product.cartID);
                    // Remove the product card from the UI
                    productCard.remove();
                });

                productCard.appendChild(removeButton);
                productList.appendChild(productCard);
            });
        })
        .catch(error => console.error('Error:', error));

    function removeCartItem(cartID) {
        // Make a DELETE request to remove the item from the cart
        fetch(`http://localhost:8080/cart/clearCartItem/${cartID}`, {
            method: 'DELETE',
        })
            .then(response => response.json())
            .then(data => console.log('Item removed from cart:', data))
            .catch(error => console.error('Error removing item from cart:', error));
    }
});





document.addEventListener("DOMContentLoaded", function() {
    fetch('http://localhost:8080/cart/getAllCartItems')
      .then(response => {
        // Retrieve the total count from the headers
        const totalCountHeader = response.headers.get('X-Total-Count');

        // Update the content of the HTML element
        const totalItemsElement = document.getElementById('totalItems');
        totalItemsElement.textContent = `Total Items In Cart - ${totalCountHeader}`;

        // Parse the JSON response
        return response.json();
      })
      .then(data => {
        // Process the data
        console.log('Cart Items:', data);
      })
      .catch(error => console.error('Error:', error));
});
