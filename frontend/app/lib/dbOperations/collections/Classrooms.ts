// >>> TEMPLATE FOR CRUD
"use server";
import axios from 'axios';

const url = `${process.env.BACKEND_URL}/classrooms`

export async function createClassroom(data: any): Promise<any> {
  try {
    const { data: createdResp } = await axios.post(url, data);
    return createdResp;

  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function readClassroom(id: any): Promise<any> {
  try {
    const { data: classroom } = await axios.get(`${url}/${id}`);
    return classroom;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function readClassrooms(): Promise<any> {
  try {
    const { data: classrooms } = await axios.get(url);
    return classrooms;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function deleteClassroom(id: any): Promise<any> {
  try {
    const { data: deletedResp } = await axios.delete(`${url}/${id}`);
    return deletedResp;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

export async function updateClassroom(id: string, updateObj: any): Promise<any> {
  try {
    const { data: updatedResp } = await axios.put(`${url}/${id}`, updateObj);
    return updatedResp;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

