<template>
  <div class="todo-container">
    <h1 class="todo-header">Todo List</h1>
    <form @submit.prevent="addTodo" class="todo-form">
      <input v-model="newTodo.description" placeholder="Enter todo description" class="todo-input" />
      <button type="submit" class="todo-button" :disabled="!newTodo.description">Add Todo</button>
    </form>

    <ul class="todo-list">
      <li v-for="todo in todos" :key="todo.id" class="todo-item">
        <button @click="toggleComplete(todo)" class="todo-toggle">
          {{ todo.completed ? '✅' : '🔳' }}
        </button>
        <span class="todo-text">{{ todo.description }}</span>
        <button @click="openEditModal(todo)" class="todo-edit">Edit</button>
        <button @click="deleteTodo(todo.id)" class="todo-delete">Delete</button>
      </li>
    </ul>

    <!-- Popis Akcí -->
    <div class="actions-description">
      <h5>Co můžete dělat:</h5>
      <ul>
        <li><strong>Přidat Todo:</strong> Vyplňte název a popis úkolu a klikněte na tlačítko <em>Add Todo</em>.</li>
        <li><strong>Přepnout stav dokončení:</strong> Klikněte na tlačítko 🔳 pro označení úkolu jako dokončeného nebo na ✅ pro jeho vrácení zpět.</li>
        <li><strong>Upravit úkol:</strong> Klikněte na tlačítko <em>Edit</em>, změňte název nebo popis úkolu v modálním okně a uložte změny.</li>
        <li><strong>Smazat úkol:</strong> Klikněte na tlačítko <em>Delete</em> pro odstranění úkolu.</li>
      </ul>
    </div>

    <!-- Edit Modal -->
    <div v-if="showModal" class="modal-overlay" @click.self="closeEditModal">
      <div class="modal-content">
        <h2>Edit Todo</h2>
        <form @submit.prevent="updateTodo" class="modal-form">
          <input v-model="currentTodo.description" placeholder="Enter todo description" class="todo-input" />
          <div class="modal-buttons">
            <button type="submit" class="todo-button" :disabled="!currentTodo.description">Save Changes</button>
            <button @click.prevent="closeEditModal" class="todo-button cancel-button">Cancel</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      todos: [],
      newTodo: {
        description: '',
        completed: false
      },
      currentTodo: null,
      showModal: false
    };
  },
  created() {
    this.fetchTodos();
  },
  methods: {
    async fetchTodos() {
      try {
        const response = await axios.get('/todoEntry');
        this.todos = response.data;
      } catch (error) {
        console.error('Error fetching todos:', error);
      }
    },
    async addTodo() {
      if (!this.newTodo.description.trim()) {
        // Prevent adding a todo if description is empty
        alert('Description cannot be empty.');
        return;
      }

      try {
        await axios.post('/todoEntry', this.newTodo);
        this.newTodo = { description: '', completed: false };
        this.fetchTodos(); // Refresh the list of todos
      } catch (error) {
        console.error('Error adding todo:', error);
      }
    },
    async updateTodo() {
      if (!this.currentTodo.description.trim()) {
        // Prevent updating a todo if description is empty
        alert('Description cannot be empty.');
        return;
      }

      try {
        await axios.put(`/todoEntry/`, this.currentTodo);
        this.closeEditModal(); // Close modal and refresh todos
        this.fetchTodos(); // Refresh the list of todos
      } catch (error) {
        console.error('Error updating todo:', error);
      }
    },
    async deleteTodo(id) {
      try {
        await axios.delete(`/todoEntry/${id}`);
        this.fetchTodos(); // Refresh the list of todos
      } catch (error) {
        console.error('Error deleting todo:', error);
      }
    },
    async toggleComplete(todo) {
    try {
        todo.completed = !todo.completed;  // Přepněte stav completed
        await axios.put(`/todoEntry/${todo.id}/completed`, 
        todo.completed,  // Odesílá se pouze boolean hodnota
        {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        this.fetchTodos();  // Obnoví seznam todo
    } catch (error) {
        console.error('Error updating completed status:', error);
    }
    },
    openEditModal(todo) {
      this.currentTodo = { ...todo };
      this.showModal = true;
    },
    closeEditModal() {
      this.showModal = false;
      this.currentTodo = null;
    }
  }
};
</script>

<style scoped>
.todo-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.todo-header {
  font-size: 2em;
  margin-bottom: 20px;
  text-align: center;
  color: #333;
}

.todo-form {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 20px;
}

.todo-input {
  padding: 10px;
  font-size: 1em;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 100%;
}

.todo-button {
  padding: 10px;
  font-size: 1em;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  width: 100%;
}

.todo-button:hover {
  background-color: #0056b3;
}

.todo-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.todo-list {
  list-style-type: none;
  padding: 0;
}

.todo-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  border-bottom: 1px solid #ddd;
  background-color: #fff;
  border-radius: 4px;
  margin-bottom: 10px;
}

.todo-toggle {
  background: none;
  border: none;
  font-size: 1.5em;
  cursor: pointer;
  transition: color 0.3s ease;
}

.todo-toggle:hover {
  color: #007bff;
}

.todo-text {
  flex-grow: 1;
  margin-left: 10px;
  font-size: 1em;
  color: #333;
}

.todo-edit, .todo-delete {
  background: none;
  border: none;
  font-size: 1em;
  cursor: pointer;
  transition: color 0.3s ease;
}

.todo-edit {
  color: #28a745;
}

.todo-edit:hover {
  color: #218838;
}

.todo-delete {
  color: #ff0000;
}

.todo-delete:hover {
  color: #cc0000;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  width: 100%;
  max-width: 500px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.modal-content h2 {
  margin-top: 0;
}

.modal-form {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.modal-input {
  width: 100%;
  margin-bottom: 20px;
}

.modal-buttons {
  display: flex;
  justify-content: center;
  gap: 10px;
  width: 100%;
}

.cancel-button {
  background-color: #dc3545;
}

.cancel-button:hover {
  background-color: #c82333;
}
.actions-description {
  margin-top: 20px;
  padding: 15px;
  background-color: #f1f1f1;
  border-radius: 8px;
  font-size: 1em;
  color: #333;
}

.actions-description h5 {
  margin-bottom: 10px;
  font-size: 1em;
  color: #007bff;
}

.actions-description ul {
  list-style-type: disc;
  padding-left: 10px;
  font-size: 14px;
}

.actions-description strong {
  color: #333;
}

.todo-button.todo-cancel {
  background-color: #6c757d;
}

.todo-button.todo-cancel:hover {
  background-color: #5a6268;
}
</style>
