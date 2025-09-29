document.addEventListener('DOMContentLoaded', function() {

    // ===========================================
    // == 1. DARK MODE LOGIC
    // ===========================================
    const themeToggle = document.getElementById('theme-checkbox');
    const docHtml = document.documentElement;

    function applyTheme() {
        const savedTheme = localStorage.getItem('theme');
        const systemPrefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;

        if (savedTheme === 'dark' || (!savedTheme && systemPrefersDark)) {
            docHtml.classList.add('dark-mode');
            if (themeToggle) themeToggle.checked = true;
        } else {
            docHtml.classList.remove('dark-mode');
            if (themeToggle) themeToggle.checked = false;
        }
    }

    if (themeToggle) {
        themeToggle.addEventListener('change', function() {
            if (this.checked) {
                docHtml.classList.add('dark-mode');
                localStorage.setItem('theme', 'dark');
            } else {
                docHtml.classList.remove('dark-mode');
                localStorage.setItem('theme', 'light');
            }
        });
    }

    applyTheme();


    // ===========================================
    // == 2. MODAL LOGIC
    // ===========================================
    const addModal = document.getElementById('add-task-modal');
    const editModal = document.getElementById('edit-task-modal');
    const openAddModalBtn = document.getElementById('quick-add-btn');
    const closeModalBtns = document.querySelectorAll('.close-btn');

    if (openAddModalBtn) {
        openAddModalBtn.addEventListener('click', () => {
            if (addModal) addModal.style.display = 'block';
        });
    }

    document.querySelectorAll('.edit-btn').forEach(button => {
        button.addEventListener('click', event => {
            const btn = event.currentTarget;
            const id = btn.dataset.id;
            const title = btn.dataset.title;
            const description = btn.dataset.description;
            const duedate = btn.dataset.duedate;
            const priority = btn.dataset.priority;
            
            if (editModal) {
                editModal.querySelector('#edit-todo-id').value = id;
                editModal.querySelector('#edit-title').value = title;
                editModal.querySelector('#edit-description').value = description;
                editModal.querySelector('#edit-dueDateTime').value = duedate;
                editModal.querySelector('#edit-priority').value = priority;
                editModal.style.display = 'block';
            }
        });
    });

    closeModalBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            if (addModal) addModal.style.display = 'none';
            if (editModal) editModal.style.display = 'none';
        });
    });

    window.addEventListener('click', event => {
        if (event.target == addModal) addModal.style.display = 'none';
        if (event.target == editModal) editModal.style.display = 'none';
    });
});