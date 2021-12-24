import axios from './httpservice'

class ContractsService {
    async getAll() {
        const response = await axios.get("contracts");
        return response.data
    }

    async create(data) {
        const response = await axios.post("contracts", data)
        return response.data
    }

    async update(data) {
        const url = "contracts/" + data.id
        const response = await axios.put(url, data)
        return response.data
    }

    async get(id) {
        const url = "contracts/" + id
        const response = await axios.get(url)
        return response.data
    }

    async remove(id) {
        const url = `contracts/${ id }`;
        const response = await axios.delete(url);
        return response.data;
    }
}

export default new ContractsService();