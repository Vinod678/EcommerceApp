



    //Cart Products Table will display
    document.addEventListener("DOMContentLoaded", function () {
        const productList = document.getElementById('cartList');
        const userId = localStorage.getItem('userId');
        const url = `http://localhost:8080/cart/getAllCartItems?userId=${userId}`;
//        const url = `http://localhost:8080/users/getUserIdByUserEmail?userEmail=${userEmail}`;

        // Fetch products from the backend
        fetch(url)
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


        // Function to check if the user is logged in , If not loggedIn it will redirect to homepage
        function isUserLoggedIn() {
            // Retrieve the login status from localStorage
            const loggedIn = localStorage.getItem('loggedIn');
            // Check if the user is logged in based on the stored value
            return loggedIn === 'true';
        }

        // Check if the user is logged in when the page loads
        if (!isUserLoggedIn()) {
            // Redirect to the homepage if the user is not logged in
            window.location.href = "http://localhost:63342/EcommerceApp/static/index.html";
        }
    });





    document.addEventListener("DOMContentLoaded", function() {
     const userId = localStorage.getItem('userId');
            const url = `http://localhost:8080/cart/getAllCartItems?userId=${userId}`;
        fetch(url)
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
     const userId = localStorage.getItem('userId');
     const url = `http://localhost:8080/cart/clearCart?userId=${userId}`
            fetch(url, {
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













// Function to fetch subtotal amount from API
function fetchSubTotal() {
    console.log('Fetching subtotal...');
    const userId = localStorage.getItem('userId');
    const url = `http://localhost:8080/cart/subTotalAmount?userId=${userId}`;

    // Make AJAX request to fetch subtotal amount
    fetch(url)
        .then(response => {
            console.log('Response status:', response.status);
            return response.json();
        })
        .then(data => {
            console.log('Subtotal data received:', data);
            // Update Subtotal-value element with fetched subtotal amount
            document.getElementById('Subtotal-value').textContent = data.toFixed(2); // Round to 2 decimal places
        })
        .catch(error => console.error('Error fetching subtotal:', error));
}

// Call fetchSubTotal function when the page loads
window.onload = function() {
    console.log('Window loaded. Fetching subtotal...');
    fetchSubTotal();
};



