async function fetchData() {
    try {
        const response = await fetch("https://jsonplaceholder.typicode.com/posts/1");

        if (response.ok) {
            const data = await response.json();
            console.log(`Fetched Data: ${data}`);
        } else {
            console.error(`Error Fetching Data: ${response.status} - ${response.statusText}`);
        }
    } catch (error) {
        console.error(`Error: ${error}`);
    }
}

fetchData().then(r => console.log('Fetch Completed'));