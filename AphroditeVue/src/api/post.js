import service from '@/utils/service';

export function FetchAllPosts() {
  return service({
    url: '/data/fetchdata_onmounted', // baseURL 是 /api，所以这里写相对路径
    method: 'get'
  });
}
