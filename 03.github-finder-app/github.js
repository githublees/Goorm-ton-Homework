class Github {
  constructor() {
    this.access_token = "";
    // repository 수
    this.repos_count = 5;
    // repository 순서
    this.repos_sort = "created: asc";
  }

  async getUser(user) {
    const profileResponse = await fetch(
      `https://api.github.com/users/${user}`,
      {
        headers: {
          Authorization: "",
        },
      }
    );

    const repoResponse = await fetch(
      `https://api.github.com/users/${user}/repos?per_page=${this.repos_count}&sort=${this.repos_sort}`,
      {
        headers: {
          Authorization: "",
        },
      }
    );

    const profile = await profileResponse.json();
    const repos = await repoResponse.json();

    return {
      profile,
      repos,
    };
  }
}
