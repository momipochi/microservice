import { apiFetch } from '@/lib/api'

export interface ProductDTO {
  id: string
  name: string
  description: string
  price: number
}

export interface ProductListingDTO {
  pages: number
  currentPage: number
  products: ProductDTO[]
}
export const listingService = {
  getListingsByQuery: async (
    query: string,
    page?: string | null,
    order?: string | null,
  ): Promise<ProductListingDTO> => {
    let term = `/listing?query=${query}`
    if (page) {
      term = `${term}&page=${page}`
    }
    if (order) {
      term = `${term}&order=${order}`
    }
    const res: ProductListingDTO = await apiFetch(term)
    return res
  },
}
