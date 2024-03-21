 // Function to parse URL parameters
    function getUrlParam(name) {
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        return urlParams.get(name);
    }

    // Get the productId from the URL
    const productId = getUrlParam('productId');

    if (!productId) {
        console.error('Product ID not provided in the URL');
    } else {
        // Fetch the product details from the API using the productId
        fetch(`http://localhost:8080/products/getProductById?productId=${productId}`)
            .then(response => response.json())
            .then(data => {
                // Extract product details from the JSON data
                const productName = data.productName;
                const productDescription = data.productDescription;
                const productPrice = data.productPrice;
                const productImage = data.productImage;
                const category = data.category;
                const averageRating = data.averageRating;
                const totalReviews = data.totalReviews;
                const productReviews = data.productReviews;

                // Construct HTML to display product details
                const productHTML = `
                    <h2>${productName}</h2>
                    <p>Product ID: ${productId}</p>
                    <p>Description: ${productDescription}</p>
                    <p>Price: ${productPrice}</p>
                    <p>Category: ${category}</p>
                    <div class="Product-Review-div">
                        <h3>Product Reviews:</h3>
                        <p>Average Rating: ${averageRating}</p>
                        <p>Total Reviews: ${totalReviews}</p>
                                        <ul class="product-reviews">
                                            ${productReviews.map(review => `
                                                <li class="review-box">
                                                    <div class="card-body">
                                                        <table class="table table-bordered">
                                                            <tbody>
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <strong>${review.userName}</strong>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <p>${review.reviewText}</p>
                                                                        <p class="m-0">${review.reviewDate}</p>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <strong>Customer Rating: </strong>
                                                                        ${review.rating} / 5
                                                                        <!-- Add logic here to display the appropriate star ratings -->
                                                                    </td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </li>
                                            `).join('')}
                                        </ul>
                    </div>
                                `;

                // Inserting product HTML into the page
                document.getElementById('product-details').innerHTML = productHTML;

                // Set product image source
                document.querySelector('.product-image').src = productImage;
            })
            .catch(error => console.error('Error fetching product details:', error));
    }