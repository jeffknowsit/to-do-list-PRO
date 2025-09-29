document.addEventListener('DOMContentLoaded', function() {

    // --- 1. DARK MODE LOGIC ---
    const themeToggle = document.getElementById('theme-checkbox');
    const docHtml = document.documentElement;
    // ... (dark mode logic remains the same)

    // --- 2. MODAL SETUP ---
    const addModal = document.getElementById('add-task-modal');
    const editModal = document.getElementById('edit-task-modal');
    const viewModal = document.getElementById('view-task-modal'); // New modal
    const openAddModalBtn = document.getElementById('quick-add-btn');
    const closeModalBtns = document.querySelectorAll('.close-btn');

    // --- 3. LOGIC FOR "ADD TASK" & "EDIT TASK" MODALS (from index.html) ---
    if (openAddModalBtn) {
        openAddModalBtn.addEventListener('click', () => {
            if (addModal) addModal.style.display = 'block';
        });
    }
    document.querySelectorAll('.edit-btn').forEach(button => {
        button.addEventListener('click', event => {
            // ... (existing edit modal logic)
        });
    });

    // ===========================================
    // == 4. NEW: CALENDAR EVENT MODAL LOGIC
    // ===========================================
    document.querySelectorAll('.event-item').forEach(eventButton => {
        eventButton.addEventListener('click', () => {
            if (!viewModal) return; // Only run if the view modal exists on the page

            // Get data from the clicked event's data-* attributes
            const data = eventButton.dataset;
            const priorities = ['None', 'Low', 'Medium', 'High'];

            // Populate the "View Task" modal with details
            viewModal.querySelector('#view-task-title').textContent = data.title;
            viewModal.querySelector('#view-task-description').textContent = data.description || 'No description provided.';
            viewModal.querySelector('#view-task-duedate').textContent = new Date(data.duedate).toLocaleString();
            viewModal.querySelector('#view-task-priority').textContent = priorities[data.priority];

            // Set the href for the delete button
            const deleteBtn = viewModal.querySelector('#view-task-delete-btn');
            deleteBtn.href = `/delete-todo/${data.id}`;
            
            // Set up the edit button to open the edit modal
            const editBtn = viewModal.querySelector('#view-task-edit-btn');
            editBtn.onclick = () => {
                viewModal.style.display = 'none'; // Hide the view modal
                // Populate the actual edit modal
                if (editModal) {
                    editModal.querySelector('#edit-todo-id').value = data.id;
                    editModal.querySelector('#edit-title').value = data.title;
                    editModal.querySelector('#edit-description').value = data.description;
                    editModal.querySelector('#edit-dueDateTime').value = data.duedate;
                    editModal.querySelector('#edit-priority').value = data.priority;
                    editModal.style.display = 'block'; // Show the edit modal
                }
            };

            viewModal.style.display = 'block';
        });
    });


    // --- 5. GENERAL MODAL CLOSING LOGIC ---
    closeModalBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            if (addModal) addModal.style.display = 'none';
            if (editModal) editModal.style.display = 'none';
            if (viewModal) viewModal.style.display = 'none'; // Close the new modal too
        });
    });
    window.addEventListener('click', event => {
        if (event.target == addModal) addModal.style.display = 'none';
        if (event.target == editModal) editModal.style.display = 'none';
        if (event.target == viewModal) viewModal.style.display = 'none'; // Close the new modal too
    });
});