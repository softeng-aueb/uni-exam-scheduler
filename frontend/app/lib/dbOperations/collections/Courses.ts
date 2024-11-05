// >>> TEMPLATE FOR CRUD
"use server";
import axios from 'axios';

const url = `${process.env.BACKEND_URL}/courses`

export async function createCourse(data: any): Promise<any> {
  try {
    const { data: createdResp } = await axios.post(url, data);
    return createdResp;

  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function readCourse(id: any): Promise<any> {
  try {
    const { data: course } = await axios.get(`${url}/${id}`);
    return course;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function readCourses(): Promise<any> {
  try {
    const { data: courses } = await axios.get(url);
    return courses;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function deleteCourse(id: any): Promise<any> {
  try {
    const { data: deletedResp } = await axios.delete(`${url}/${id}`);
    return deletedResp;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function updateCourse(id: string, updateObj: any): Promise<any> {
  try {
    const { data: updatedResp } = await axios.put(`${url}/${id}`, updateObj);
    return updatedResp;
  } catch (e: any) {
    return { status: "Failed" }
  }
}


