import axios from "axios";

const AccountAPI = axios.create({
  baseURL: "http://localhost:8080/api/accounts",
  auth: {
      username: "admin",
      password: "admin123"
    }
});


export default AccountAPI;