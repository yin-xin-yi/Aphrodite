import service from "@/utils/service";

export const FetchAllPosts = async () => {
  console.log("FetchAllPosts");
  
  return await service.get("/data/fetchdata_onmounted");
};
export const FetchPostDetail = async (postid) => {
  return await service.get(`/data/fetchpost/${postid}`);
};
export const CreatePost = async (post_inf) => {
  return await service.post("/data/addpost", post_inf);
};
