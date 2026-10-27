<script setup>
import { ref, onMounted } from 'vue'

const bookings = ref([])

async function getBookings() {
  const response = await fetch('http://localhost:8080/bookings')
  bookings.value = await response.json()
}

onMounted(getBookings)
</script>

<template>
  <h2>All Bookings</h2>

  <div class="grid">
    <div class="card" v-for="b in bookings" :key="b.id">
      <h3>Booking ID: {{ b.id }}</h3>
      <p>Movie ID: {{ b.movieId }}</p>
      <p>Customer: {{ b.customerName }}</p>
      <p>Tickets: {{ b.numberOfTickets }}</p>
      <p>Price: ₹{{ b.price }}</p>
      <p>Status: {{ b.status }}</p>
    </div>
  </div>
</template>

<style scoped>
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  padding: 15px;
}

.card {
  border: 1px solid #ccc;
  border-radius: 10px;
  padding: 15px;
  background: #f9f9f9;
  transition: 0.3s;
}

.card:hover {
  background: #e6efff;
  transform: translateY(-5px);
}

.card h3 {
  color: royalblue;
}
</style>
