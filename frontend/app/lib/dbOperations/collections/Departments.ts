// >>> TEMPLATE FOR CRUD
"use server";
import axios from 'axios';

const BASE_URL = `${process.env.BACKEND_URL}/departments`

export async function readDepartments(): Promise<any> {
  try {
    const { data: res } = await axios.get(BASE_URL)
    return res;
  } catch (e: any) {
    return { status: "Failed" }
  }
}

// TEMP
export async function createDepartment(): Promise<any> {
  try {
    return {};
  } catch (e: any) {
    return { status: "Failed" }
  }
}

// TEMP
export async function updateDepartment(): Promise<any> {
  try {
    return {};
  } catch (e: any) {
    return { status: "Failed" }
  }
}
