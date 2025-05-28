const BASE_URL = import.meta.env.VITE_API_BASE_URL

export const apiFetch = async (path: string, options?: RequestInit) => {
  const res = await fetch(`${BASE_URL}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...(options?.headers || {}),
    },
    ...options,
  })

  if (!res.ok) {
    throw new Error(`API error: ${res.status}`)
  }

  return await res.json()
}
