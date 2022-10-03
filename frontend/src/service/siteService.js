import axios from '../service/interceptor'

export const siteService = {
  getPropertiesOfSite
};
function getPropertiesOfSite(element) {
  const url = 'api/getpropertiesofsite';
  axios
    .post(url)
    .then((res) => {
      element.current.value = JSON.stringify(res.data);
    });
}
