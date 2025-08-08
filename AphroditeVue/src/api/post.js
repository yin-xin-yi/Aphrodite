import service from "@/utils/service";
import { GetUserId } from "../utils/auth";

export const FetchAllPosts = async () => {
  const userid = GetUserId();
  return await service.get(`/data/fetchdata_onmounted/${userid}`);
};
export const FetchPostDetail = async (postId) => {
  return await service.get(`/data/fetchpost/${postId}`, {
    params: {
      userId: GetUserId() || null,
    },
  });
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
