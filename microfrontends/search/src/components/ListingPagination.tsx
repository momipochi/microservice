import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from './ui/pagination'

interface PaginationModel {
  pages: number
  currentPage: number
  query: string // e.g. "query=something"
}

const getPageHref = (query: string, page: number) => {
  return `/listing?query=${query}&page=${page}`
}

export const ListingPagination = ({
  pages,
  currentPage,
  query,
}: PaginationModel) => {
  const maxVisiblePages = 5
  const pageNumbers: number[] = []

  const half = Math.floor(maxVisiblePages / 2)
  let startPage = Math.max(1, currentPage - half)
  let endPage = Math.min(pages, currentPage + half)

  if (currentPage <= half) {
    endPage = Math.min(pages, maxVisiblePages)
  } else if (currentPage + half > pages) {
    startPage = Math.max(1, pages - maxVisiblePages + 1)
    endPage = pages
  }

  for (let i = startPage; i <= endPage; i++) {
    pageNumbers.push(i)
  }

  return (
    <div>
      <Pagination>
        <PaginationContent>
          {/* Previous */}
          <PaginationItem>
            <PaginationPrevious
              href={currentPage > 1 ? getPageHref(query, currentPage - 1) : '#'}
            />
          </PaginationItem>

          {/* First page and ellipsis */}
          {startPage > 1 && (
            <>
              <PaginationItem>
                <PaginationLink href={getPageHref(query, 1)}>1</PaginationLink>
              </PaginationItem>
              {startPage > 2 && (
                <PaginationItem>
                  <PaginationEllipsis />
                </PaginationItem>
              )}
            </>
          )}

          {/* Visible page numbers */}
          {pageNumbers.map((page) => (
            <PaginationItem key={page}>
              <PaginationLink
                href={getPageHref(query, page)}
                isActive={page === currentPage}
              >
                {page}
              </PaginationLink>
            </PaginationItem>
          ))}

          {/* Last page and ellipsis */}
          {endPage < pages && (
            <>
              {endPage < pages - 1 && (
                <PaginationItem>
                  <PaginationEllipsis />
                </PaginationItem>
              )}
              <PaginationItem>
                <PaginationLink href={getPageHref(query, pages)}>
                  {pages}
                </PaginationLink>
              </PaginationItem>
            </>
          )}

          {/* Next */}
          <PaginationItem>
            <PaginationNext
              href={
                currentPage < pages ? getPageHref(query, currentPage + 1) : '#'
              }
            />
          </PaginationItem>
        </PaginationContent>
      </Pagination>
    </div>
  )
}
