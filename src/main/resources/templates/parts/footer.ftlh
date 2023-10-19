<footer class="footer py-1 bg-light" style="position: fixed; bottom: 0;
width: 100%; justify-content: center; padding: 10%; max-height: 80px">
    <div style="display: flex">
        <div style="flex: 1; text-align: center; justify-content: center; margin: auto">
            <p id="about-creator" class="text-muted"></p>
            <a href="https://t.me/arsen_igorevich"><p id="about-contacts" class="text-muted"></p></a>
        </div>
        <div style="flex: 1; text-align: center; justify-content: center; margin: auto">
            <p id="about-description" class="text-muted"></p>
        </div>
    </div>
</footer>
<script>
    fetch('http://localhost:5001/api/about')
        .then(response => response.json())
        .then(data => {
            document.getElementById('about-creator').textContent = "by " + data.creator;
            document.getElementById('about-contacts').textContent = data.contacts;
            document.getElementById('about-description').textContent = data.description;
        });
</script>