document.addEventListener("DOMContentLoaded", function() {
    // Function to retrieve query parameters from the URL
    function getQueryParam(name) {
        const urlSearchParams = new URLSearchParams(window.location.search);
        return urlSearchParams.get(name);
    }


    // Retrieve product ID from query parameters
    const productId = getQueryParam('productId');

    // Fetch product details from the API
    fetch(`http://localhost:8080/products/getProductById?productId=${productId}`)
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(product => {
        // Display product details on the page
        const productDetailsElement = document.getElementById('productDetails');
        productDetailsElement.innerHTML = `
            <img src="${product.productImage}" alt="${product.productName}" width="115" height="100">
            <p>Product ID: ${product.productID}</p>
            <p>Product Name: ${product.productName}</p>
            <p>Product Price: ${product.productPrice}</p>
            <p>Product Description: ${product.productDescription}</p>
        `;
        // Call the function to handle quantity and total cost calculations
        //Remove the & symbol from the product price
        const productPrice = parseFloat(product.productPrice.match(/\d+\.\d+/)); //
        handleQuantityAndTotalCost(productPrice);
         // Initialize sessionStorage with initial values if not already set
                if (!sessionStorage.getItem(`quantity_${productId}`)) {
                    sessionStorage.setItem(`quantity_${productId}`, '1');
                }
                if (!sessionStorage.getItem(`totalCost_${productId}`)) {
                    sessionStorage.setItem(`totalCost_${productId}`, (productPrice * parseInt(sessionStorage.getItem(`quantity_${productId}`))).toFixed(2));
                }

                // Update the displayed quantity and total cost based on sessionStorage values
                const inputField = document.getElementById('input');
                const totalValue = document.getElementById('total-value');
                inputField.value = sessionStorage.getItem(`quantity_${productId}`);
                totalValue.textContent = '$' + sessionStorage.getItem(`totalCost_${productId}`);

                // Populate modal content with order details when Review Order button is clicked
                        const reviewOrderButton = document.getElementById('reviewOrderButton');
                       reviewOrderButton.onclick = function() {
                           modal.style.display = 'block';
                           const orderReviewContent = document.getElementById('orderReviewContent');
                           const { quantity, totalCost } = getStoredQuantityAndTotalCost(productId);
                           orderReviewContent.innerHTML = `
                               <!-- Order details dynamically populated from sessionStorage -->
                               <h3>Product Name: ${product.productName}</h3>
                               <p>Product ID: ${product.productID}</p>
                               <p>Quantity: <span id="modalQuantity">${quantity}</span></p>
                               <p>Total Price: <span id="modalTotalPrice">$${totalCost}</span></p>
                               <button id="placeOrderButton">Place Order</button>
                           `;
                       };




  })
    .catch(error => {
        console.error('Error fetching product details:', error);
        // Display error message on the page if fetching fails
        const productDetailsElement = document.getElementById('productDetails');
        productDetailsElement.innerHTML = `<p>Error fetching product details: ${error.message}</p>`;
    });





// Function to handle quantity and total cost calculations
    function handleQuantityAndTotalCost(productPrice) {
        // Quantity increase and decrease In Order Page
        const inputField = document.getElementById('input');
        const totalValue = document.getElementById('total-value');

        function updateValue(newValue) {
            const minValue = parseInt(inputField.min);
            const maxValue = parseInt(inputField.max);
            if (!isNaN(minValue) && newValue < minValue) {
                newValue = minValue;
            }
            if (!isNaN(maxValue) && newValue > maxValue) {
                newValue = maxValue;
            }
            inputField.value = newValue;
            updateTotalCost();
            updateSessionStorage(); // Update sessionStorage whenever quantity changes
        }

        function updateTotalCost() {
            const quantity = parseInt(inputField.value);
            const totalCost = productPrice * quantity;
            totalValue.textContent = '$' + totalCost.toFixed(2); // Display total cost with dollar sign
        }

        const minusButton = document.getElementById('minus');
        const plusButton = document.getElementById('plus');

        minusButton.addEventListener('click', event => {
            event.preventDefault();
            updateValue(parseInt(inputField.value) - 1);
        });

        plusButton.addEventListener('click', event => {
            event.preventDefault();
            updateValue(parseInt(inputField.value) + 1);
        });

        inputField.addEventListener('input', event => {
            const newValue = parseInt(event.target.value);
            if (!isNaN(newValue)) {
                updateValue(newValue);
            }
        });

        // Calculate total cost when the page loads
        updateTotalCost();
    }

 // Function to update sessionStorage with quantity and total cost
    function updateSessionStorage() {
        const productId = getQueryParam('productId');
        sessionStorage.setItem(`quantity_${productId}`, document.getElementById('input').value);
        sessionStorage.setItem(`totalCost_${productId}`, document.getElementById('total-value').textContent.slice(1)); // Remove the dollar sign before storing
    }


    // Modal popup functionality
        const modal = document.getElementById('orderReviewModal');
        const closeBtn = document.getElementsByClassName('close')[0];

        closeBtn.onclick = function() {
            modal.style.display = 'none';
        }

        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = 'none';
            }
        }



        function getStoredQuantityAndTotalCost(productId) {
            const quantity = sessionStorage.getItem(`quantity_${productId}`);
            const totalCost = sessionStorage.getItem(`totalCost_${productId}`);
            return { quantity, totalCost };
        }

});