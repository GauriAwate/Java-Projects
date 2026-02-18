function renderBooks(books) {
    const container = document.getElementById("bookList");
    container.innerHTML = "";
    books.forEach(b => {
        const div = document.createElement("div");
        div.innerHTML = `<h3>${b.title}</h3>
                         <p>Author: ${b.author}</p>
                         <p>Price: $${b.price}</p>`;
        container.appendChild(div);
    });
}
