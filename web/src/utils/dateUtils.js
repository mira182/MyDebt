export default {
    methods: {
        parseDate (date) {
            if (!date) return null;

            const [day, month, year] = date.split('-');
            return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
        },
        formatDate (date) {
            if (!date) return null;

            const [year, month, day] = date.split('-');
            return `${day}-${month}-${year}`
        }
    },
    filters: {
        formatDate (date) {
            if (!date) return null;

            const [year, month, day] = date.split('-');
            return `${day}-${month}-${year}`
        }
    }
}