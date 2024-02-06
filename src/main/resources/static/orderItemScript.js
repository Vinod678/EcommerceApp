//
//    //Quantity increase and descrease In Order Page
//
//     const minusButton = document.getElementById('minus');
//     const plusButton = document.getElementById('plus');
//     const inputField = document.getElementById('input');
//     const totalValue = document.getElementById("total-value");
//
//     function updateValue(newValue) {
//         const minValue = parseInt(inputField.min);
//         const maxValue = parseInt(inputField.max);
//         if (!isNaN(minValue) && newValue < minValue) {
//             newValue = minValue;
//         }
//         if (!isNaN(maxValue) && newValue > maxValue) {
//             newValue = maxValue;
//         }
//         inputField.value = newValue;
//         updateTotalCost();
//     }
//
//    function updateTotalCost() {
//        const productPriceString = document.querySelector('#productDetails > p:nth-child(3)').textContent;
//        console.log("Product Price String:", productPriceString);
//        const productPrice = parseFloat(productPriceString.match(/\d+\.\d+/)); // Extract numeric value using regex
//        console.log("Parsed Product Price:", productPrice);
//        const quantity = parseInt(inputField.value);
//        console.log("Quantity:", quantity);
//        const totalCost = productPrice * quantity;
//    //    totalValue.textContent = totalCost.toFixed(2); // Display total cost with 2 decimal places
//        totalValue.textContent = '$' + totalCost.toFixed(2); // Display total cost with dollar sign
//    }
//
//     minusButton.addEventListener('click', event => {
//         event.preventDefault();
//         updateValue(parseInt(inputField.value) - 1);
//     });
//
//     plusButton.addEventListener('click', event => {
//         event.preventDefault();
//         updateValue(parseInt(inputField.value) + 1);
//     });
//
//     inputField.addEventListener('input', event => {
//         const newValue = parseInt(event.target.value);
//         if (!isNaN(newValue)) {
//             updateValue(newValue);
//         }
//     });
//
//    // Calculate total cost when the page loads
//    document.addEventListener('DOMContentLoaded', () => {
//        updateTotalCost();
//    });










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




//    //Quantity increase and descrease In Order Page
document.addEventListener("DOMContentLoaded", function() {
    const productDetailsElement = document.getElementById('productDetails');

    if (productDetailsElement) {
        const productPriceString = productDetailsElement.querySelector('p:nth-child(3)').textContent;
        console.log("Product Price String:", productPriceString);
        const productPrice = parseFloat(productPriceString.match(/\d+\.\d+/)); // Extract numeric value using regex
        console.log("Parsed Product Price:", productPrice);
        const inputField = document.getElementById('input');
        const totalValue = document.getElementById("total-value");

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
        }

        function updateTotalCost() {
            const quantity = parseInt(inputField.value);
            console.log("Quantity:", quantity);
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
    } else {
        console.error('Element with id "productDetails" not found.');
    }
});
