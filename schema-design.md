# Schema Design (MySQL)

Database name: `cms`

## Tables

### 1) doctor
Stores doctors and their specialties.

**Columns**
- `id` BIGINT PRIMARY KEY AUTO_INCREMENT
- `name` VARCHAR(100) NOT NULL
- `specialty` VARCHAR(100) NOT NULL
- `email` VARCHAR(150) UNIQUE
- `phone` VARCHAR(20)

---

### 2) patient
Stores patients.

**Columns**
- `id` BIGINT PRIMARY KEY AUTO_INCREMENT
- `name` VARCHAR(100) NOT NULL
- `phone` VARCHAR(20) NOT NULL
- `email` VARCHAR(150) UNIQUE

---

### 3) appointment
Stores appointments booked by patients with doctors.

**Columns**
- `id` BIGINT PRIMARY KEY AUTO_INCREMENT
- `doctor_id` BIGINT NOT NULL
- `patient_id` BIGINT NOT NULL
- `appointment_time` DATETIME NOT NULL
- `status` VARCHAR(30) NOT NULL

**Relationships**
- `appointment.doctor_id` → `doctor.id` (FK)
- `appointment.patient_id` → `patient.id` (FK)

---

## SQL DDL

```sql
CREATE DATABASE IF NOT EXISTS cms;
USE cms;

CREATE TABLE doctor (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  specialty VARCHAR(100) NOT NULL,
  email VARCHAR(150) UNIQUE,
  phone VARCHAR(20),
  PRIMARY KEY (id)
);

CREATE TABLE patient (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  email VARCHAR(150) UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE appointment (
  id BIGINT NOT NULL AUTO_INCREMENT,
  doctor_id BIGINT NOT NULL,
  patient_id BIGINT NOT NULL,
  appointment_time DATETIME NOT NULL,
  status VARCHAR(30) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id),
  CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);
