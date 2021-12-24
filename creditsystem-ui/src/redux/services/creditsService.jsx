import axios from './httpservice'

class CreditsService {
    async getAll() {
        const response = await axios.get(`credits`);
        return response.data;
    }

    async getFiltered({
                     maxSum,
                     minSum,
                     monthsDuration,
                     earningPercentage,
                     earningPercentageAfterDeadline
                 }) {
        const response = await axios.get(`credits?
        max-sum=${maxSum}
        &min-sum=${minSum}
        &months-duration=${monthsDuration}
        &earning-percentage=${earningPercentage}
        &earning-percentage-after-deadline=${earningPercentageAfterDeadline}`);
        return response.data;
    }

    async create(data) {
        const response = await axios.post("credits", data)
        return response.data
    }

    async update(data) {
        const url = "credits/" + data.id
        const response = await axios.put(url, data)
        return response.data
    }

    async get(id) {
        const url = "credits/" + id
        const response = await axios.get(url)
        return response.data
    }

    async remove(id) {
        const url = `credits/${id}`;
        const response = await axios.delete(url);
        return response.data;
    }
}

export default new CreditsService();
