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
                                            // Construct the URL with product details as query parameters
                                            const orderItemUrl = `http://localhost:63342/EcommerceApp/static/orderItem.html?productId=${product.productID}&productName=${encodeURIComponent(product.productName)}&productPrice=${product.productPrice}`;
                                            window.location.href = orderItemUrl;
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
                const orderButton = document.createElement('button');

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
                    location.reload();
                });


                orderButton.innerText = 'Buy';
                orderButton.addEventListener('click', function(){
                     // Construct the URL with product details as query parameters
                     const orderItemUrl_1 = `http://localhost:63342/EcommerceApp/static/orderItem.html?productId=${product.product.productID}&productName=${encodeURIComponent(product.product.productName)}&productPrice=${product.product.productPrice}`;
                     window.location.href = orderItemUrl_1;
                 });

                productCard.appendChild(removeButton);
                productCard.appendChild(orderButton);
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
      .then(response => response.json())
      .then(data => {
        // Calculate total count
        const totalCount = data.reduce((acc, item) => acc + item.quantity, 0);
        console.log('totalCount - ', totalCount);

        // Update the content of the HTML element
        const totalItemsElement = document.getElementById('totalItems');
        totalItemsElement.textContent = `Total Items In Cart - ${totalCount}`;
      })
      .catch(error => console.error('Error:', error));
});



// Function to Delete All Products
 function deleteAllProducts() {
        fetch('http://localhost:8080/cart/clearCart', {
            method: 'DELETE'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

                location.reload();
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }




//To display order details
document.addEventListener("DOMContentLoaded", function() {
            // Function to retrieve query parameters from the URL
            function getQueryParam(name) {
                const urlSearchParams = new URLSearchParams(window.location.search);
                return urlSearchParams.get(name);
            }

            // Retrieve product details from query parameters
            const productId = getQueryParam('productId');
            const productName = getQueryParam('productName');
            const productPrice = getQueryParam('productPrice');
//            const quantity = getQueryParam('quantity');

            // Display product details on the page
            const productDetailsElement = document.getElementById('productDetails');
            productDetailsElement.innerHTML = `
                <p>Product ID: ${productId}</p>
                <p>Product Name: ${productName}</p>
                <p>Product Price: ${productPrice}</p>
            `;

            console.log('Product ID:', productId);
            console.log('Product Name:', productName);
            console.log('Product Price:', productPrice);


            // Button click event to place the order (add your logic here)
            const placeOrderButton = document.getElementById('placeOrderButton');
            placeOrderButton.addEventListener('click', function() {
                // Add your logic to place the order
                alert('Order placed successfully!');
            });
        });