import service from "@/utils/service";
import { GetUserId } from "../utils/auth";

export const FetchAllPosts = async () => {
  console.log("FetchAllPosts",GetUserId());
  const userId = GetUserId();
  return await service.get(`/data/fetchdata_onmounted/${userId}`);
};
export const FetchPostDetail = async (postid) => {
  return await service.get(`/data/fetchpost/${postid}`);
};
export const CreatePost = async (post_inf) => {
  return await service.post("/data/addpost", post_inf);
};

export const GetSelfPosts = async (user_id) => {
  console.log("GetSelfPosts -> ", user_id);
  return await service.get(`/data/selfpost/${user_id}`);
};

export const DelPost = async (post_id) => {
  console.log("DelPost -> ", post_id);
  return await service.post("/data/delpost", {
    postId: post_id,
  });
};
