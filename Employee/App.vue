<script setup>
import { ref, onMounted } from 'vue'

const api = 'http://localhost:8080/api'

const employees = ref([])
const payrolls = ref([])

const employee = ref({
  employeeId: '',
  deptId: '',
  deptName: 'IT',
  employeeName: ''
})

// GET IT EMPLOYEES

async function getEmployees() {
  employees.value =
    await (await fetch(api + '/employees/it')).json()
}

// POST EMPLOYEE

async function addEmployee() {
  await fetch(api + '/employees', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(employee.value)
  })

  employee.value = {
    employeeId: '',
    deptId: '',
    deptName: 'IT',
    employeeName: ''
  }

  await getEmployees()
  await getPayrolls()
}

// GET IT PAYROLLS

async function getPayrolls() {
  payrolls.value =
    await (await fetch(api + '/payrolls/it')).json()
}

// GET EMPLOYEE NAME FROM ID

function employeeName(id) {
  const e = employees.value.find(e => e.employeeId === id)
  return e ? e.employeeName : id
}

onMounted(() => {
  getEmployees()
  getPayrolls()
})
</script>

<template>
  <h1>Employee Management System</h1>

  <!-- ADD EMPLOYEE -->

  <h2>Add Employee</h2>

  <form @submit.prevent="addEmployee">
    <input
      v-model="employee.employeeId"
      placeholder="Employee ID"
      required
    />

    <input
      v-model="employee.deptId"
      placeholder="Department ID"
      required
    />

    <input
      v-model="employee.deptName"
      placeholder="Department Name"
      required
    />

    <input
      v-model="employee.employeeName"
      placeholder="Employee Name"
      required
    />

    <button type="submit">Add Employee</button>
  </form>

  <!-- DISPLAY IT EMPLOYEES -->

  <h2>Employees in IT Department</h2>

  <table border="1" cellpadding="8">
    <tr>
      <th>Employee ID</th>
      <th>Department ID</th>
      <th>Department</th>
      <th>Employee Name</th>
    </tr>

    <tr v-for="e in employees" :key="e.employeeId">
      <td>{{ e.employeeId }}</td>
      <td>{{ e.deptId }}</td>
      <td>{{ e.deptName }}</td>
      <td>{{ e.employeeName }}</td>
    </tr>
  </table>

  <!-- DISPLAY IT PAYROLLS -->

  <h2>Payrolls of IT Employees</h2>

  <table border="1" cellpadding="8">
    <tr>
      <th>Payroll ID</th>
      <th>Employee ID</th>
      <th>Employee Name</th>
      <th>Basic Salary</th>
      <th>Allowances</th>
      <th>Deductions</th>
      <th>Net Salary</th>
    </tr>

    <tr v-for="p in payrolls" :key="p.payrollId">
      <td>{{ p.payrollId }}</td>
      <td>{{ p.employeeId }}</td>
      <td>{{ employeeName(p.employeeId) }}</td>
      <td>{{ p.basicSalary }}</td>
      <td>{{ p.allowances }}</td>
      <td>{{ p.deductions }}</td>
      <td>{{ p.netSalary }}</td>
    </tr>
  </table>
</template>
